var express = require('express'),
    event = require('events').EventEmitter,
    User = require('../models/user');

var routes = function(){
    var usersRouter = express.Router(),
        EventEmitter = new event();

    usersRouter.route('/')
        .get(function(req, res){  
            //Return all users
            User.find({}, '-password')
            .then(function (err, users) {
              if (err) return res.send(err);
                res.status(200).json(users);
            })
        });   

    usersRouter.route('/:id')
        .get(function(req, res){
            //Return a specific user
            User.findById(req.params.id, '-password')
            .then(function(user){
                res.json({user: user});
            })
        }); 

    usersRouter.route('/email/:email')
        .get(function(req, res){
            //Return user
            User.findOne({email: req.params.email}, '-password')
            .then(function(user){
                res.json({user: user});
            })
        }); 

    usersRouter.route('/msisdn/:msisdn')
        .get(function(req, res){
            //Return user 
            User.findOne({phone: req.params.msisdn}, '-password')
            .then(function(user){
                res.json({user: user});
            })     
        }); 
    
     /**
     * Update a user
     *
    */
    usersRouter.route('/:id')
        .put(function(req, res){
            var userId = req.params.id;
            User.findOneAndUpdate(
                {_id: userId},
                {$set: {
                    firstname: req.body.firstname,
                    lastname: req.body.lastname,
                    email : req.body.email,
                    phone: req.body.phone,
                    modifiedDate: new Date
                }},
                {safe: true},
                function(err, user){
                    if(err) return res.status(500).json({success: false, error: err});
                    return res.json({success: true, message: 'user details updated'});
                }
            );

        });
    /**
     * Register a user
     *
    */
    usersRouter.route('/')
        .post(function(req, res){
            // validation
            req.checkBody('firstname', 'firstname field is required').notEmpty();
            req.checkBody('lastname', 'lastname field is required').notEmpty();
            req.checkBody('email', 'email field is required').notEmpty();
            req.checkBody('password', 'password field is required').notEmpty();
            var errors = req.validationErrors();
            if (errors) return res.status(422).json({success: false, errors: errors});

            //check if phone or email exist
            User.findOne({email: req.body.email})
            .then(function(user){
                if(user) return res.json({success: false, message: 'registration failed. User already registered'});

                var newUser = new User;
                newUser.firstname = req.body.firstname;
                newUser.lastname = req.body.lastname;
                newUser.email = req.body.email;
                newUser.phone = req.body.phone;
                newUser.password = newUser.generateHash(req.body.password);
                
                newUser.save(function(err){
                    newUser = newUser.toObject();
                    delete newUser.password; //remove password hash from returned object
                    if(err) return res.json({success: false, message: 'Error registering. try again'});
                    return res.json({success: true, message: 'registration successful', user: newUser})
                });
            });
                     
        });
    
    /**
     * Delete a user
     *
    */
    usersRouter.route('/:id')
        .delete(function(req, res){
            User.remove({_id: req.params.id})
            .then(function(resp){
                if(resp.n > 0) return res.json({success: 'user deleted'});
                return res.json({success: false, message: 'user not deleted'})
            })
            .catch(function(err){
                res.status(500).json({success: false, error: err});
            })
        })
    return {router: usersRouter, event: EventEmitter};
};

module.exports = routes;