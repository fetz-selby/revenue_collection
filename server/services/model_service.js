var service = {},
	user = {},
	district = {},
	agent = {},
	people = {},
	property = {};

service = {	'user' : function () {
				return require('../models/user');
			},
			'district' : function(){
				return require('../models/district');
			},
			'people' : function(){
				return require('../models/people');
			},
			'agent' : function(){
				return require('../models/agent');
			},
			'property' : function(){
				return require('../models/property');
			},
			'region' : function(){
				return require('../models/region');
			}
}

module.exports = service;