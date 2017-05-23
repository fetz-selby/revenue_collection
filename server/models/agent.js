var mongoose = require('mongoose-fill'),
    Schema = mongoose.Schema,
	bcrypt   = require('bcrypt-nodejs');

var agentSchema = new Schema({
    firstname       : String,
    surname         : String,
    othernames      : String,
    phone           : String,
    email           : String,
    password        : String,
    district        : { type: Schema.Types.ObjectId },
    identification  : [{
        type	    : String,
        number 		: String,
        picture		: String
    }],
    createdBy       : { type: Schema.Types.ObjectId, ref: 'User' },
    createdDate     : { type: Date, default: Date.now },
    modifiedDate    : { type: Date, default: Date.now },
    status          : { type: String, default: 'A'}
}, {
    toObject: {
        virtuals: true
    },
    toJSON: {
        virtuals: true 
    }
});

agentSchema.methods.generateHash = function(password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

// checking if password is valid
agentSchema.methods.validPassword = function (password) {
    return bcrypt.compareSync(password, this.password);
};
agentSchema.methods.getRegion = function(cb){
     Region.findOne({'districts._id': this.district})
    .then(function(reg){
        cb('ads');
    })
}


module.exports = mongoose.model('Agent', agentSchema);