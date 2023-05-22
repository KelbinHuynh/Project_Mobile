'use strict'

const util = require('util')
const mysql = require('mysql')
const { response } = require('express')
const {login, register, uploadAvatar, editInforUser} = require('../service/user.service')

module.exports = {
    //Login with user
    login: async(req, res) => {
        try {
            var data = req.body;
            const result =  await login(data);
            console.log(result)
            if (result.length > 0) {
                res.json({ success: true, message: 'Login successfull', data: result[0] })
            }else{
                res.json({ success: false, message: 'Wrong username or password', data: result[0] })
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    //Register with user
    register: async(req, res) => {
        try {
            var data = req.body;
            // if (req.file)
            //     data["avatar"] = req.file.filename;
            const result = await register(data);
            console.log(result)
            if (result.length > 0) {
                res.json({ success: true, message: 'Create succcessful', data: result[0] })
            }
            else {
                res.json({ success: false, message: 'Create failed', data: result[0] })
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    uploadAvatar: async(req, res) => {
        
        try {    
            var data = req.body;
            console.log(data.username);
            if (req.file)
                data["avatar"] = req.file.filename;
            console.log(req.file.filename)
            const result = await uploadAvatar(data);
            console.log(result)
            if (result.length > 0) {
                res.json({ success: true, message: 'Upload succcessful', data: result[0] })
            }
            else {
                res.json({ success: false, message: 'Upload failed', data: result[0] })
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

    editInforUser: async(req, res) => {
        
        try {    
            var data = req.body;

            const result = await editInforUser(data);
            console.log(result)
            if (result.length > 0) {
                res.json({ success: true, message: 'Upload succcessful', data: result[0] })
            }
            else {
                res.json({ success: false, message: 'Upload failed', data: result[0] })
            }
        } catch (e) {
            return res.status(500).json({
                "message": "Internal Server Error"
            });
        }
    },

}