const db = require('../dbConnection')
const util = require('util')
const { upload } = require('../helpers/image_uploaders')
const host = require('../host')
const tzoffset = (new Date()).getTimezoneOffset() * 60000;

const query = util.promisify(db.query).bind(db)

module.exports = {

    getAllCategory: async() =>{
        let response
        let categorySql = `SELECT * FROM category`
        let styleSql = `SELECT style_id, style_name, createdAt, updatedAt
                    FROM styles WHERE category_id=?`
        
        try {
            
            const responseCategory = await query(categorySql)
            response = responseCategory
            for(var i in responseCategory){
                const responseStyle = await query(styleSql, responseCategory[i].category_id)
                response[i]['style'] = responseStyle
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