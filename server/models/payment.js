var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var paymentSchema = new Schema({
	people   			: { type: Schema.Types.ObjectId, ref: 'People' },
	payments 			: [{
		amount 			: Number,
		createdBy 		: { type: Schema.Types.ObjectId, ref: 'Agent' },
		date 			: Date,
		createdDate 	: { type: Date, default: Date.now }
		modifiedDate 	: { type: Date, default: Date.now }
	}]
	
	
});

module.exports = mongoose.model('Payment', paymentSchema);
