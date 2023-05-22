'use strict'

const util = require('util')
const mysql = require('mysql')
const { response } = require('express')
const {getOrderToUser, addOrderToUser, getAllOrder, updateStatusOrder, getSales} = require('../service/order.service')

module.exports = {
    getOrderToUser: async(req, res) => {
        try {
            var data = req.body;
            const result =  await getOrderToUser(data);
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

    addOrderToUser: async(req, res) => {
        try {
            var data = req.body;
            const result =  await addOrderToUser(data);
            console.log(result)
            res.json(result)
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    getAllOrder: async(req, res) => {
        try {
            var data = req.body;
            const result =  await getAllOrder();
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
    updateStatusOrder: async(req, res) => {
        try {
            var data = req.body;
            const result =  await updateStatusOrder(data);
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

    getSales: async(req, res) => {
        try {
            var data = req.body;
            const result =  await getSales();
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