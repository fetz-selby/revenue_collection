var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var marketSchema = new Schema({
	name   			: String,
	district 		: { type: Schema.Types.ObjectId, ref: 'District' }
	location 		: String
	
});

module.exports = mongoose.model('Market', marketSchema);
