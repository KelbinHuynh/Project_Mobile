'use strict'

const util = require('util')
const mysql = require('mysql')
const db = require('../dbConnection')
const { response } = require('express')

module.exports = {
    login: (req, res) => {
        let sql = `SELECT * FROM user WHERE username = ? and password = ?`
        console.log(req.body.username)
        console.log(req.body.password)
        try {
            db.query(sql, [req.body.username, req.body.password], (err, response) => {
                if (response.length > 0) {
                    res.json({ success: true, message: 'Login susscessul', data: response })
                }
                else {
                    res.json({ success: false, message: 'Wrong username or password', data: response })
                }
            })
        }
        catch (error) {
            console.error(error)
            res.json({ success: false, message: 'Error!', data: error })
        }

    },
    register: (req, res) => {
        let sql = `insert into user(username, password, firstname, lastname, gender, email, phone, role, addresses)
                values(?, ?, ?, ?, ?, ?, ?, ?, ?)`
        try {
            db.query(sql, [req.body.username, req.body.password, req.body.firstname, req.body.lastname,
            req.body.gender, req.body.email, req.body.phone, req.body.role, req.body.addresses],
                (err, response) => {
                    if (err) {
                        console.error(err)
                        res.json({ success: false, message: 'Create failed', data: err })
                    }
                    else {
                        res.json({ success: true, message: 'Create susscessul', data: response })
                    }
                })
        }
        catch (error) {
            console.error(error)
            res.json({ success: false, message: 'Error!', data: error })
        }
    }
}