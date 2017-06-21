var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var marketSchema = new Schema({
	name   			: String,
	district 		: { type: Schema.Types.ObjectId, ref: 'District' },
	location 		: String
	categories		: [{
		name		: String,
		amount		: {
			daily	: Number,
			weekly	: Number,
			quarterly: Number,
			yearly	: Number
		}
	}]
	
});

module.exports = mongoose.model('Market', marketSchema);
