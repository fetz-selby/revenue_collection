var express = require('express'),
    event = require('events').EventEmitter,
    Agent = require('../models/agent');

        var agentsRouter = express.Router(),
            EventEmitter = new event();

    var routes = function(){
        agentsRouter.route('/')
            .get(function(req, res) {
                //Return all Agents
                
                Agent.find({}, '-password')
                .fill('region').exec()
                .then(function(agents) {
                    res.json({agents: agents});
                })
                .catch(function (err) {
                    res.send(err)
                })
            });

        agentsRouter.route('/:id')
            .get(function(req, res) {
                //Return a specific agent
                Agent.findOne({_id: req.params.id})
                .fill('region').exec()
                .then(function(agent) {
                    res.status(200).json({agent: agent});
                })
                .catch(function (err) {
                   res.status(400).send(err)
                })
            });

        agentsRouter.route('/email/:email')
            .get(function(req, res) {
                var email = req.params.email;
                Agent.findOne({email: email})
                    .fill('region').exec()
                    .then(function(agent) {
                        res.status(200).json({agent: agent});
                    })
                    .catch(function (err) {
                       res.status(400).send(err)
                    })
            });

        //update agent
        agentsRouter.route('/:id')
            .put(function(req, res) {
                Agent.findOneAndUpdate(
                    {_id: req.params.id},
                    { $set : {
                        firstname: req.body.firstname,
                        surname: req.body.surname,
                        othernames: req.body.othernames,
                        email : req.body.email,
                        phone: req.body.phone,
                        district: req.body.district,
                        modifiedDate: new Date
                    }},
                    {safe: true}                
                , function(err, agent){
                    if(err) return res.status(500).json({success: false, error: err});
                    return res.json({success: true, message: 'Update successful'});
                })

            });

        //delete agent
        agentsRouter.route('/:id')
            .delete(function(req, res) {
                Agent.remove({_id: req.params.id})
                .then(function(resp){
                    if(resp.n > 0) return res.json({success: true, message: 'Agent removed successfully'});
                    return res.json({success: false, message: 'Agent not removed'});
                })
                .catch(function(err){
                    res.send({error: err});
                })
            });

        //save agent
        agentsRouter.route('/')
            .post(function(req, res) {
                // validation
                req.checkBody('firstname', 'firstname field is required').notEmpty();
                req.checkBody('surname', 'surname field is required').notEmpty();
                req.checkBody('phone', 'phone field is required').notEmpty();
                req.checkBody('district', 'district field is required').notEmpty();
                req.checkBody('password', 'password field is required').notEmpty();

                var errors = req.validationErrors();
                if (errors) return res.status(422).json({success: false, errors: errors});

                //check if phone or email exist
                Agent.findOne({$or: [{phone: req.body.phone},{email: req.body.email}]} )
                .then(function(agent){
                    if(agent) return res.json({success: false, message: 'registration failed. Agent already registered'});

                    var newAgent = new Agent;
                    newAgent.firstname = req.body.firstname;
                    newAgent.surname = req.body.surname;
                    newAgent.othernames = req.body.othernames;
                    newAgent.phone = req.body.phone;
                    newAgent.email = req.body.email;
                    newAgent.password = newAgent.generateHash(req.body.password);
                    newAgent.district = req.body.district;
                    

                    newAgent.save(function(err){
                        if(err) return res.json({success: false, message: 'Error registering. try again'});
                        return res.json({success: true, message: 'registration successful', agent: newAgent})
                    });
                });
            });

        return { router: agentsRouter, event: EventEmitter };
    }

module.exports = routes;
