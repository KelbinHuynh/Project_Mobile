const db = require('../dbConnection')
const util = require('util')
const host = require('../host')
const query = util.promisify(db.query).bind(db)

module.exports = {

    selectAllAvailableFood: async() =>{
        let response

        let showFoodSql = `SELECT food_id, food_name, style_name, description, price, size, count, rated, numOfRate, createdAt, updatedAt 
        FROM pet_foods, styles WHERE isActive = 1 and isDeleted = 0 and pet_foods.style_id = styles.style_id ORDER BY updateAt ASC `
        
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updateAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const responsePet = await query(showPetSql)
            response = responsePet
            
            for (let i = 0; i < 6; i++){
                const responseImage = await query(showImageSql, responsePet[i].pets_id)
                response[i]['imagesList'] = responseImage

                const responseStyle = await query(showStyleSql, responsePet[i].pets_id)
                response[i]['styleList'] = responseStyle
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