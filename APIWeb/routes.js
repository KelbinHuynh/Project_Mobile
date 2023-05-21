'use strict';
module.exports = function(app) {
  let loginCtrl = require('./controllers/loginController');

  app.route('/login').post(loginCtrl.login);
  app.route('/register').post(loginCtrl.register);
}