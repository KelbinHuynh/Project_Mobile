'use strict'

const util = require('util')
const mysql = require('mysql')
const { response } = require('express')
const {getAllCategory} = require('../service/category.service')

module.exports = {
    getAllCategory: async(req, res) => {
        try {
            const result =  await getAllCategory();
            console.log(result)
            if (result.length > 0) {
                res.json(result)
            }else{
                res.json(result)
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },
}