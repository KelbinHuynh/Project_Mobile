const db = require('../dbConnection')
const util = require('util')
const { upload } = require('../helpers/image_uploaders')
const host = require('../host')
const tzoffset = (new Date()).getTimezoneOffset() * 60000;

const query = util.promisify(db.query).bind(db)

module.exports = {

    login: async(data) =>{
        let sql = `SELECT user_id, username, name, CONCAT('` +host+ `upload/', avatar) AS avatar, gender.gender_name, email, phone, role.role_name, addresses, createdAt, updatedAt
                    FROM user, role, gender WHERE role.role_id = user.role_id and gender.gender_id = user.gender_id and username = ? and password = ?`
        console.log(data.username)
        console.log(data.password)
        try {
            const response = await query(sql, [data.username, data.password])
            
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    register: async(data) => {
        let insertSql = `insert into user(username, password, name, gender_id, email, phone, addresses)
                values(?, ?, ?, ?, ?, ?, ?)`

        let sql = `SELECT user_id, username, name, CONCAT('` +host+ `upload/', avatar) AS avatar, gender.gender_name, email, phone, role.role_name, addresses, createdAt, updatedAt
                FROM user, role, gender WHERE role.role_id = user.role_id and gender.gender_id = user.gender_id and user_id = ?` 
        let cartSql = `insert into cart(user_id)
        values(?)`
        try {
            
            const insertResponse = await query(insertSql, [data.username, data.password, data.name, data.gender_id, data.email, data.phone, data.addresses]) 
            const cartRespone = await query(cartSql, [insertResponse.insertId])
            const response  = await query(sql, [insertResponse.insertId])
            return response
                
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    uploadAvatar: async(data) => {
        let uploadSql = `UPDATE user SET avatar = ?, updatedAt = ? WHERE username = ?`
        let sql = `SELECT user_id, username, name, CONCAT('` +host+ `upload/', avatar) AS avatar, gender.gender_name, email, phone, role.role_name, addresses, createdAt, updatedAt
        FROM user, role, gender WHERE role.role_id = user.role_id and gender.gender_id = user.gender_id and username = ?`
        var updatedAt = (new Date(Date.now() - tzoffset)).toISOString().replace('T', ' ').slice(0, -5)
        
        try {
            
            const responseUpload = await query(uploadSql, [data.avatar, updatedAt, data.username]) 
            console.log(responseUpload)

            const response = await query(sql, [data.username])   
            console.log(response) 
            return response
                
        }
        catch (error) {
            console.error(error)
            return error
        }

    },

    editInforUser: async(data) => {
        let uploadSql = `UPDATE user SET name = ?, email = ?, addresses = ?, phone = ? ,updatedAt = ? WHERE user_id= ?`
        let sql = `SELECT user_id, username, name, CONCAT('` +host+ `upload/', avatar) AS avatar, gender.gender_name, email, phone, role.role_name, addresses, createdAt, updatedAt
        FROM user, role, gender WHERE role.role_id = user.role_id and gender.gender_id = user.gender_id and user_id = ?`
        var updatedAt = (new Date(Date.now() - tzoffset)).toISOString().replace('T', ' ').slice(0, -5)
        
        try {
            
            const responseUpload = await query(uploadSql, [data.name, data.email, data.addresses, data.phone, updatedAt, data.user_id]) 
            console.log(responseUpload)

            const response = await query(sql, [data.user_id])   
            console.log(response) 
            return response
                
        }
        catch (error) {
            console.error(error)
            return error
        }

    }
}