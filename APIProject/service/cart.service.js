const db = require('../dbConnection')
const util = require('util')
const { upload } = require('../helpers/image_uploaders')
const host = require('../host');

const tzoffset = (new Date()).getTimezoneOffset() * 60000;
const query = util.promisify(db.query).bind(db)

module.exports = {
    getCartToUser: async(data) =>{
        let response
        let showCartSql = `SELECT *  FROM cart WHERE user_id = ?`
        let showDetailSql = `SELECT cartdetail_id, pets_id, count_SP, createdAt, updatedAt  FROM cart_detail WHERE cart_id = ?`
        let showPetSql = `SELECT pets_id, pets_name, weight, age, gender, price, count, rated, numOfRate, createdAt, updatedAt 
                    FROM pets WHERE pets_id = ?`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const responseCart = await query(showCartSql, data.user_id)
            
            const responseDetail = await query(showDetailSql, responseCart[0].cart_id)
            response = responseCart
            
            for (var i in responseDetail){
                const responsePet = await query(showPetSql, responseDetail[i].pets_id)

                const responseImage = await query(showImageSql, responsePet[0].pets_id)
                responsePet[0]['imagesList'] = responseImage

                const responseStyle = await query(showStyleSql, responsePet[0].pets_id)
                responsePet[0]['styleList'] = responseStyle
                responseDetail[i]['pets'] = responsePet[0]
            }

            response[0]['cart_detail'] = responseDetail
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    addProductToCart: async(data) =>{
        let showCartSql = `SELECT *  FROM cart WHERE user_id = ?`
        let insertSql = `INSERT INTO cart_detail(cart_id, pets_id, count_SP) VALUES(?,?,?)`
        let showDetailSql = `SELECT cartdetail_id, pets_id, count_SP, createdAt, updatedAt  FROM cart_detail WHERE cart_id = ?`
       
        try {
            const responseCart = await query(showCartSql, data.user_id)

            const responseInsert = await query(insertSql, [responseCart[0].cart_id, data.pets_id, data.count_SP])
            
            if (responseInsert.insertId != null){
                response = { success: true, message: 'Insert succcessful'} 
            }else {
                response = { success: false, message: 'Insert failed'} 
            }
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    checkProductToCart: async(data) =>{
        let showCartSql = `SELECT *  FROM cart WHERE user_id = ?`
        let showDetailSql = `SELECT cartdetail_id, pets_id, count_SP, createdAt, updatedAt  FROM cart_detail WHERE cart_id = ? and pets_id = ?`
       
        try {
            const responseCart = await query(showCartSql, data.user_id)

            const responseDetail = await query(showDetailSql, [responseCart[0].cart_id, data.pets_id])
        
            if (responseDetail.length > 0){
                response = {success: true, message: 'Had Product'}
            }else {
                response = {success: false, message: 'Not have Product'}
            }
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    updateCountOfItem: async(data) =>{
        let updateSql =  `UPDATE cart_detail SET count_SP = ? WHERE cartdetail_id = ?`
        try {
            const responseUpdate = await query(updateSql, [data.count_SP, data.cartdetail_id])
            
            if (responseUpdate.affectedRows != 0 ){
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

    deleteCartDetail: async(data) =>{
        let deleteSql =  `DELETE FROM cart_detail WHERE cartdetail_id = ?`
        try {
            const responseDelete = await query(deleteSql, data.cartdetail_id)
            
            if (responseDelete.affectedRows != 0 ){
                response = {success: true, message: 'Delete Successful'}
            }else{
                response = {success: false, message: 'Delete Failed'}
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