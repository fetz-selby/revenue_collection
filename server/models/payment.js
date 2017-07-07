var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var paymentSchema = new Schema({
	seller   			: { type: Schema.Types.ObjectId, ref: 'Seller' },
	amount 			: Number,
	createdBy 		: { type: Schema.Types.ObjectId, ref: 'Agent' },
	type			: { type: String, enum: ['daily', 'yearly'] },
	paymentYear 	: String,
	date 			: Date,
	createdDate 	: { type: Date, default: Date.now },
	modifiedDate 	: { type: Date, default: Date.now }



});

module.exports = mongoose.model('Payment', paymentSchema);
