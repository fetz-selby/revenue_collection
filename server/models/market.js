var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var marketSchema = new Schema({
	name   			: String,
	stores			: [{
		store		: String,
		price		: Number,
		year		: Number
	}]
});

module.exports = mongoose.model('Market', marketSchema);
