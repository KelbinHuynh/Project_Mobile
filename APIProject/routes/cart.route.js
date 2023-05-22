'use strict';
const router = require("express").Router();
const cartCtrl = require('../controllers/cart.controller');
const imageUploader = require("../helpers/image_uploaders")


router.post('/api/getCartToUser', cartCtrl.getCartToUser);
router.post('/api/addProductToCart', cartCtrl.addProductToCart);
router.post('/api/checkProductToCart', cartCtrl.checkProductToCart);
router.post('/api/updateCountOfItem', cartCtrl.updateCountOfItem);
router.post('/api/deleteCartDetail', cartCtrl.deleteCartDetail);
module.exports = router;