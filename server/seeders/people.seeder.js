var Seeder = require('mongoose-data-seed').Seeder;
var Region = require('../models/region');
var People = require('../models/people');
var faker = require('faker');

var PeopleSeeder = Seeder.extend({
    beforeRun: function(){
        var _this = this;
        _this.postsData = _this._generateData();
        return true;
    },
    shouldRun: function () {
        return true;//People.count().exec().then(count => count === 0);
    },
    run: function () {
        return People.create(this.postsData);
    },
    _generateData: function(){
        var peoples = [];
        for(var i = 0; i < 2000; i++){
            var people = {
                firstname: faker.name.firstName(),
                surname: faker.name.lastName(),
                dob: faker.date.past(),
                birthPlace: faker.address.city(),
                nationality: 'Ghanaian',
                address 		: {
                    residential : faker.address.streetAddress(),
                    postal 		: faker.address.streetAddress(),
                    work 		: faker.address.streetAddress()
                },
                phone: [faker.phone.phoneNumber()],
                email: faker.internet.email(),
                phone: faker.image.imageUrl()                    
            }
            peoples.push(people);
        }
        
        return peoples;
        
    }
});

module.exports = PeopleSeeder;
