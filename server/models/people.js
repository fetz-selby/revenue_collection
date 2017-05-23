var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var mongoosePaginate = require('mongoose-paginate');

var peopleSchema = new Schema({
	firstname		: String,
	surname			: String,
	othernames 		: String,
	dob 			: Date,
	birthPlace 		: String,
	nationality 	: String,
	nationalityType : String,
	dualCitizenship	: [String],
	ethnicity 		: String,
	maritalStatus 	: String,
	languages 		: {
		spoken 		: [String],
		written 	: [String]
	},
	phones			: [String],
	email 			: String,
	address 		: {
		residential : String,
		postal 		: String,
		work 		: String
	},
	region 			: String,
	districtType 	: String,
	homeGps			: {
        lat		: String,
        lng		: String		
	},
	what3words 		: {
		home		: String,
		work 		: String
	},
	photo 			: String,
	employmentStatus: String,
	employer 		: String,
	occupation 		: String,
    tin             : String,
	commencementDate: Date,
	position 		: String,
	employmentSector: String,
    guid            : String,
	disability		: [String],
	identification	: {
		type  		: { type: String, default:'' },
		number		: { type: String, default:'' },
		picture		: { type: String, default:'' }
	},
	
	createdBy 		: { type: Schema.Types.ObjectId, ref: 'Agent' },
	createdDate 	: { type: Date, default: Date.now },
	modifiedDate	: { type: Date, default: Date.now },
	status 			: { type: String, default: 'A'}
});

peopleSchema.plugin(mongoosePaginate);
module.exports = mongoose.model('People', peopleSchema);



/*

Property collection

2. Photo of Building

3. Photo of Certificate of Indenture

4. Finger Print(s)

*/








// 