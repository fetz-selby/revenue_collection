var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	bcrypt   = require('bcrypt-nodejs');

// user & agent
var userSchema = new Schema({
	firstname       : { type: String, trim: true },
    lastname        : { type: String, trim: true },
    email           : { type: String, unique: true },
    password 		: String,
    phone		    : { type: String, unique: true },
    picture 		: String,
    createdDate		: { type: Date, default: Date.now },
    modifiedDate	: { type: Date, default: Date.now },
    status          : { type: String, default: 'A'}
});

// methods ======================
// generating a hash
userSchema.methods.generateHash = function(password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

// checking if password is valid
userSchema.methods.validPassword = function (password) {
    return bcrypt.compareSync(password, this.password);
};


module.exports = mongoose.model('User', userSchema);