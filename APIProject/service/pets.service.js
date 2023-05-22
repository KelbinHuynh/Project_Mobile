const db = require('../dbConnection')
const util = require('util')
const host = require('../host')
const query = util.promisify(db.query).bind(db)

module.exports = {

    selectAvailableNewPet: async(data) =>{
        let response
        let showPetSql = `SELECT pets_id, pets_name, weight, age, gender, price, count, rated, numOfRate, createdAt, updatedAt 
                    FROM pets WHERE isActive = 1 and isDeleted = 0 ORDER BY updatedAt ASC LIMIT 6`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
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

    showListImagePet: async(data) =>{
        let sql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        try {
            const response = await query(sql, [data.pets_id])
            
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    showListStylePet: async(data) =>{
        let sql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const response = await query(sql, [data.pets_id])
            
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    selectAvailableCategoryPet: async() =>{
        let response
        let showCategoryPet = `SELECT distinct category.category_id, category.category_name, category.createdAt, category.updatedAt
                     FROM pets, styles, pet_style, category WHERE pets.isActive = 1 and pets.isDeleted = 0 
                     and  pets.pets_id = pet_style.pets_id and styles.style_id = pet_style.pets_style 
                     and styles.category_id = category.category_id`
        let showPetSql = `SELECT distinct pets.pets_id, pets.pets_name, pets.weight, pets.age, pets.gender, pets.price, pets.count,
                            pets.rated, pets.numOfRate, pets.createdAt, pets.updatedAt 
                        FROM pets, styles, pet_style, category WHERE pets.isActive = 1 and pets.isDeleted = 0 
                            and  pets.pets_id = pet_style.pets_id and styles.style_id = pet_style.pets_style 
                            and styles.category_id = category.category_id and category.category_name = ?
                        ORDER BY updatedAt ASC`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const responseCate = await query(showCategoryPet)
            
            response = responseCate
            
            for (var i in responseCate){
                const responsePet = await query(showPetSql, responseCate[i].category_name)
                for(var j in responsePet){
                    const responseImage = await query(showImageSql, responsePet[j].pets_id)
                    responsePet[j]['imagesList'] = responseImage
                    
                    const responseStyle = await query(showStyleSql, responsePet[j].pets_id)
                    responsePet[j]['styleList'] = responseStyle
                }
                response[i]['pets'] = responsePet
            }
            
            console.log(response)
            return response
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    selectInformationPet: async(data) =>{
        let response
        let showPetSql = `SELECT pets_id, pets_name, weight, age, gender, price, count, rated, numOfRate, createdAt, updatedAt 
                    FROM pets WHERE pets_id = ?`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            const responsePet = await query(showPetSql, data.pets_id)
            response = responsePet
                
            const responseImage = await query(showImageSql, responsePet[0].pets_id)
            response[0]['imagesList'] = responseImage

            const responseStyle = await query(showStyleSql, responsePet[0].pets_id)
            response[0]['styleList'] = responseStyle
            
            console.log(response)
            return response[0]
        }
        catch (error) {
            console.error(error)
            return error
        }
    },

    filterPetToStyle: async(data) =>{
        let response
        let showPetSql = `SELECT distinct pets.pets_id, pets_name, weight, age, gender, price, count, rated, numOfRate, createdAt, updatedAt 
                    FROM pets, pet_style WHERE pets.pets_id = pet_style.pets_id AND pet_style.pets_style in (?)`
        let showImageSql = `SELECT image_id, CONCAT('` +host+ `upload/',imageLink) AS imageLink,
                    createdAt, updatedAt FROM image_list_pets WHERE pets_id = ?`
        let showStyleSql = `SELECT style_id, style_name, createdAt, updatedAt FROM styles, pet_style WHERE styles.style_id = pet_style.pets_style and pet_style.pets_id = ?`
        try {
            var styleList = data.style
            console.log(data)
            console.log(styleList)
            const responsePet = await query(showPetSql, [styleList])
            console.log(responsePet)
            response = responsePet
            for (var i in responsePet){
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