var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var mongoosePaginate = require('mongoose-paginate');

var sellerSchema = new Schema({
	uuid			: String,
	firstname		: String,
	surname			: String,
	othernames 		: String,
	photo 			: String,
	section			: String,
	lane			: String,
	category		: String,
	type 			: String,
	sellingSpot		: String,
	mobileMoney		: {
		network 	: String,
		number 		: String
	},
	createdBy 		: { type: Schema.Types.ObjectId, ref: 'Agent' },
	createdDate 	: { type: Date, default: Date.now },
	modifiedDate	: { type: Date, default: Date.now },
	status 			: { type: String, default: 'A' }
});

sellerSchema.plugin(mongoosePaginate);
module.exports = mongoose.model('Seller', sellerSchema);
