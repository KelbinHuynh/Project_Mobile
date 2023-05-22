'use strict'

const util = require('util')
const mysql = require('mysql')
const { response } = require('express')
const {selectAvailableNewPet, selectAvailableCategoryPet, showListImagePet, showListStylePet,
    selectInformationPet, filterPetToStyle} = require('../service/pets.service')

module.exports = {
    //Select 6 availabe new pet
    selectAvailableNewPet: async(req, res) => {
        try {
            var data = req.body;
            const result =  await selectAvailableNewPet(data);
            console.log(result)
            if (result.length > 0) {
                res.json(result )
            }else{
                res.json(result )
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    selectAvailableCategoryPet: async(req, res) => {
        try {
            const result =  await selectAvailableCategoryPet();
            console.log(result)
            
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    showListImagePet: async(req, res) => {
        try {
            var data = req.body;
            const result =  await showListImagePet(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    }, 

    showListStylePet: async(req, res) => {
        try {
            var data = req.body;
            const result =  await showListStylePet(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    selectInformationPet: async(req, res) => {
        try {
            var data = req.body;
            const result =  await selectInformationPet(data);
            console.log(result)
            if (result.length > 0) {
                res.json(result )
            }else{
                res.json(result )
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    filterPetToStyle: async(req, res) => {
            console.log(req.body);
            var data = req.body;
            
            const result =  await filterPetToStyle(data);
            console.log(result)
            if (result.length > 0) {
                res.json(result )
            }else{
                res.json(result )
            }
        // } catch (e) {
        //     return res.status(500).json({
        //         "message": "Internal Server Error"
        //     });
        // }
    },
}