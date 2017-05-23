var Seeder = require('mongoose-data-seed').Seeder;
var Agent = require('../models/agent');
var Region = require('../models/region');
var faker = require('faker');

var AgentsSeeder = Seeder.extend({
    beforeRun: function(){
        var _this = this;
        return Region
            .find({})
            .exec()
            .then(function (regions) {
                _this.regions = regions;
                _this.postsData = _this._generateData();
            });
    },
    shouldRun: function () {
        return true;// Agent.count().exec().then(count => count === 0);
    },
    run: function () {
        return Agent.create(this.postsData);
    },
    _generateData: function(){
        var agents = [];
        for(var region of this.regions){
            for(var district of region.districts){
                var agent1 = {
                    firstname: faker.name.firstName(),
                    surname: faker.name.lastName(),
                    phone: faker.phone.phoneNumber(),
                    email: faker.internet.email(),
                    district: district._id                    
                }
                var agent2 = {
                    firstname: faker.name.firstName(),
                    surname: faker.name.lastName(),
                    phone: faker.phone.phoneNumber(),
                    email: faker.internet.email(),
                    district: district._id
                }
                
                agents.push(agent1);
                agents.push(agent2);
            }
            
        }
        
        return agents;
        
    }
});

module.exports = AgentsSeeder;
