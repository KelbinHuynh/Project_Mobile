'use strict';
const router = require("express").Router();
const orderCtrl = require('../controllers/order.controller');
const imageUploader = require("../helpers/image_uploaders")

router.post('/api/getOrderToUser', orderCtrl.getOrderToUser);
router.post('/api/addOrderToUser', orderCtrl.addOrderToUser);
router.get('/api/getAllOrder', orderCtrl.getAllOrder);
router.get('/api/getSales', orderCtrl.getSales);
router.post('/api/updateStatusOrder', orderCtrl.updateStatusOrder);



module.exports = router;