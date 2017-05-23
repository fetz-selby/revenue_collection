var Seeder = require('mongoose-data-seed').Seeder;
var Model = require('../models/user');
var fake = require('faker');

var data = [];

var UsersSeeder = Seeder.extend({
  shouldRun: function () {
    return true;//Model.count().exec().then(count => count === 0);
  },
  run: function () {
    return Model.create(data);
  }
});

module.exports = UsersSeeder;
