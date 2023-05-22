const db = require('../dbConnection')
const util = require('util')
const { upload } = require('../helpers/image_uploaders')
const host = require('../host');

const tzoffset = (new Date()).getTimezoneOffset() * 60000;
const query = util.promisify(db.query).bind(db)

module.exports = {
    getOrderToUser: async(data) =>{
        let response
        let showOrderSql = `SELECT * FROM ` + '`order`' +` WHERE user_id = ? ORDER BY order_id DESC`
        let showStatusSql = `SELECT status_id, status_name FROM status WHERE status_id = ?`
        let showDetailSql = `SELECT orderdetail_id, pets_id, count_SP, createdAt, updatedAt  FROM order_detail WHERE order_id = ?`
        let showPetSql = `SELECT pets_id, pets_name, weight, age, gender, price, count, rated, numOfRate, createdAt, updatedAt 
                    FROM pets WHERE pets_id = ?`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const responseOrder = await query(showOrderSql, data.user_id)

            response = responseOrder
            
            for (var i in responseOrder){
                const responseStatus = await query(showStatusSql, responseOrder[i].status_order)
                const responseDetail = await query(showDetailSql, responseOrder[i].order_id)

                for (var j in responseDetail){
                const responsePet = await query(showPetSql, responseDetail[j].pets_id)

                const responseImage = await query(showImageSql, responsePet[0].pets_id)
                responsePet[0]['imagesList'] = responseImage

                const responseStyle = await query(showStyleSql, responsePet[0].pets_id)
                responsePet[0]['styleList'] = responseStyle
                responseDetail[j]['pets'] = responsePet[0]
                }
                response[i]['order_detail'] = responseDetail
                response[i]['status_order'] = responseStatus[0]
            }

            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    getAllOrder: async() =>{
        let response
        let showOrderSql = `SELECT * FROM ` + '`order`' +` ORDER BY order_id DESC`
        let showStatusSql = `SELECT status_id, status_name FROM status WHERE status_id = ?`
        let showDetailSql = `SELECT orderdetail_id, pets_id, count_SP, createdAt, updatedAt  FROM order_detail WHERE order_id = ?`
        let showPetSql = `SELECT pets_id, pets_name, weight, age, gender, price, count, rated, numOfRate, createdAt, updatedAt 
                    FROM pets WHERE pets_id = ?`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const responseOrder = await query(showOrderSql)

            response = responseOrder
            
            for (var i in responseOrder){
                const responseStatus = await query(showStatusSql, responseOrder[i].status_order)
                const responseDetail = await query(showDetailSql, responseOrder[i].order_id)

                for (var j in responseDetail){
                const responsePet = await query(showPetSql, responseDetail[j].pets_id)

                const responseImage = await query(showImageSql, responsePet[0].pets_id)
                responsePet[0]['imagesList'] = responseImage

                const responseStyle = await query(showStyleSql, responsePet[0].pets_id)
                responsePet[0]['styleList'] = responseStyle
                responseDetail[j]['pets'] = responsePet[0]
                }
                response[i]['order_detail'] = responseDetail
                response[i]['status_order'] = responseStatus[0]
            }

            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    getSales: async(data) =>{
        let response
        let getSalesSql = `SELECT COUNT(distinct order_id) as count_order, SUM(total_price) as sum_sales, MONTH(updatedAt) as dateOrder
        FROM (SELECT ` + '`order`' + `.order_id, order_detail.count_SP, pets.price, order_detail.count_SP*pets.price as total_price, ` + '`order`' + `.updatedAt
            FROM ` + '`order`' + `, order_detail, pets 
            WHERE ` + '`order`' + `.order_id = order_detail.order_id and order_detail.pets_id = pets.pets_id)a GROUP BY MONTH(updatedAt);`
        
        
        try {
            response = await query(getSalesSql)
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    addOrderToUser: async(data) =>{
        let response
        let addOrderSql = `INSERT INTO ` + '`order`' +` (user_id, address, phone, status_order) values (?, ?, ?, ?)`
        let addOrderDetailSql = `INSERT INTO order_detail(order_id, pets_id, count_SP) values (?, ?, ?)`
        let deleteCartDetailSql = `DELETE FROM cart_detail WHERE cartdetail_id = ?`
        let showCartSql = `SELECT *  FROM cart WHERE user_id = ?`
        let showDetailSql = `SELECT *  FROM cart_detail WHERE cart_id = ?`
        let updatePetSql = `UPDATE pets SET count = ? WHERE pets_id = ?`
        let showPetSql = `SELECT *  FROM pets WHERE pets_id = ?`
        
        try {
            const responseAdd = await query(addOrderSql, [data.user_id, data.address, data.phone, 1])

            const responseShowCart = await query(showCartSql, data.user_id)
            const responseShowDetailCart = await query(showDetailSql, responseShowCart[0].cart_id)
            response = responseAdd
            
            for (var i in responseShowDetailCart){
                const responseAddDetail = await query(addOrderDetailSql, [responseAdd.insertId, responseShowDetailCart[i].pets_id, responseShowDetailCart[i].count_SP])
                const responseDeleteCart = await query(deleteCartDetailSql, responseShowDetailCart[i].cartdetail_id)
                const responseSelectPet = await query(showPetSql, responseShowDetailCart[i].pets_id)
                const responseUpdatepets = await query(updatePetSql, [  responseSelectPet[0].count - responseShowDetailCart[i].count_SP, responseShowDetailCart[i].pets_id] )

            }

            if (response.affectedRows != 0 ){
                response = {success: true, message: 'Add Successful'}
            }else{
                response = {success: false, message: 'Add Failed'}
            }
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    updateStatusOrder: async(data) =>{
        let response
        let updateOrderSql = `UPDATE ` + '`order`' +` SET status_order = ? WHERE order_id = ?`
        
        
        try {
            response = await query(updateOrderSql, [data.status_order, data.order_id])


            if (response.affectedRows != 0 ){
                response = {success: true, message: 'Update Successful'}
            }else{
                response = {success: false, message: 'Update Failed'}
            }
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },
}