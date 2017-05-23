var express = require('express'),
	Agent 	= require('../models/agent'),
	User 	= require('../models/user');

var routes = function(){
    var authRouter = express.Router();

    /*
	 * Login
	 *
	 *
    */
    authRouter.route('/login')
	    .post(function(req, res){
	    	var email = req.body.email,
				password = req.body.password;

			req.checkBody('password', 'password field is required').notEmpty();

		    var errors = req.validationErrors();
			if (errors) return res.status(422).json({success: false, errors: errors});



			User.findOne({$or: [{phone: email},{email: email}]} )
			.then(function(user){
		        // if no user is found, return the message
		        if (!user)
		            return res.status(422).json({success: false, message:'Invalid username/password.', user: null});

		        // if the user is found but the password is wrong
		        if (!user.validPassword(password))
		            return res.status(422).json({success: false, message:'Invalid username/password.', user:null});


		        // login successful
		        user = user.toObject();
		        delete user.password;
		        return res.json({ success: true, message:'Login successful', user: user });
		    });

	    });
        
    authRouter.route('/agents/login')
	    .post(function(req, res){
	    	var email = req.body.email,
				password = req.body.password;

			req.checkBody('email', 'email field is required').notEmpty();
			req.checkBody('password', 'password field is required').notEmpty();

		    var errors = req.validationErrors();
			if (errors) return res.status(422).json({success: false, errors: errors});



			Agent.findOne({$or: [{phone: email},{email: email}]} )
//            .fill()
			.then(function(agent){
		        // if no user is found, return the message
		        if (!agent)
		            return res.status(422).json({success: false, message:'Invalid username/password.'});

		        // if the user is found but the password is wrong
		        if (!agent.validPassword(password))
		            return res.status(400).json({success: false, message:'Invalid username/password.'});


		        // login successful
		        agent = agent.toObject();
		        delete agent.password;
		        return res.json({ success: true, message:'Login successful', agent: agent });
		    });

	    });

	authRouter.route('/signup')
        .post(function(req, res){
        	
        });

    return { router: authRouter };
};
module.exports = routes;