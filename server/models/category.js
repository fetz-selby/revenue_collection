var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var categorySchema = new Schema({
	name   		: String,
	rate		: Number	
});

module.exports = mongoose.model('Category', categorySchema);
