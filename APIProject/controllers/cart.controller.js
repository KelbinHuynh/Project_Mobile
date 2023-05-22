'use strict'

const util = require('util')
const mysql = require('mysql')
const { response } = require('express')
const {getCartToUser, addProductToCart, checkProductToCart, updateCountOfItem, deleteCartDetail} = require('../service/cart.service')

module.exports = {
    getCartToUser: async(req, res) => {
        try {
            var data = req.body;
            const result =  await getCartToUser(data);
            console.log(result)
            if (result.length > 0) {
                res.json(result[0] )
            }else{
                res.json(result[0] )
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    addProductToCart: async(req, res) => {
        try {
            var data = req.body;
            const result =  await addProductToCart(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    checkProductToCart: async(req, res) => {
        try {
            var data = req.body;
            const result =  await checkProductToCart(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    updateCountOfItem: async(req, res) => {
        try {
            var data = req.body;
            const result =  await updateCountOfItem(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    deleteCartDetail: async(req, res) => {
        try {
            var data = req.body;
            const result =  await deleteCartDetail(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },
}