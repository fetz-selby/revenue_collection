var mongooseLib = require('mongoose'),
    dbConfig = require('./config'),
    userSeeder = require('./seeders/users.seeder.js'),
    agentSeeder = require('./seeders/agents.seeder.js'),
    peopleSeeder = require('./seeders/people.seeder.js'),
    propertySeeder = require('./seeders/property.seeder.js');


mongooseLib.Promise = global.Promise;

module.exports = {

  // Export the mongoose lib
  mongoose: mongooseLib,

  // Export the mongodb url
  mongoURL: 'mongodb://'+dbConfig.config.db_instance,

  /*
    Seeders List
    ------
    order is important
  */
  seedersList: {
//      userSeeder,
//      agentSeeder,
//      peopleSeeder,
  }
};
