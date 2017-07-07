var express = require('express'),
    event = require('events').EventEmitter,
    Agent = require('../models/agent');

        var agentsRouter = express.Router(),
            EventEmitter = new event();

    var routes = function(){
        /**
         * @api {get} /agents  List agents
         * @apiDescription Get list of all agents
         * @apiGroup Agent
         * @apiVersion 1.0.0
         *
         * @apiSuccessExample {json} Success-Response:
         *     HTTP/1.1 200 OK
         *     {
         *       "agents": []
         *     }
         */
        agentsRouter.route('/')
            .get(function(req, res) {
                //Return all Agents
                
                Agent.find({}, '-password')
                .sort('-createdAt')
                .then(function(agents) {
                    res.json({agents: agents});
                })
                .catch(function (err) {
                    res.send(err)
                })
            });

        /**
         * @api {get} /agents/:id  Get specific agents
         * @apiDescription Get a single agent
         * @apiGroup Agent
         * @apiVersion 1.0.0
         *
         * @apiParam {String} id unique agent id
         *
         * @apiSuccessExample {json} Success-Response:
         *     HTTP/1.1 200 OK
         *     {
         *       "agents": {}
         *     }
         */
        agentsRouter.route('/:id')
            .get(function(req, res) {
                //Return a specific agent
                Agent.findOne({_id: req.params.id})
                .populate('market')
                .then(function(agent) {
                    res.status(200).json({agent: agent});
                })
                .catch(function (err) {
                   res.status(500).send(err)
                })
            });




        // agentsRouter.route('/:field/:email')
        //     .get(function(req, res) {
        //         var email = req.params.email;
        //         Agent.findOne({email: email})
        //             .then(function(agent) {
        //                 res.status(200).json({agent: agent});
        //             })
        //             .catch(function (err) {
        //                res.status(500).send(err)
        //             })
        //     });

         /**
         * @api {put} /agents/:id Update agent
         * @apiDescription Update agent details
         * @apiGroup Agent
         * @apiVersion 1.0.0
         *
         * @apiParam {String} id unique agent id
         * @apiParam {String} firstname firstname of agent
         * @apiParam {String} surname surname of agent
         * @apiParam {String} phone phone number of agent
         *
         * @apiSuccessExample {json} Success-Response:
         *     HTTP/1.1 200 OK
         *     {
         *      "success": true,
         *      "message": "Success message"
         *     }
         *
         * @apiErrorExample {json} Error-Response:
         *     HTTP/1.1 4xx Unprocessible Entity
         *     {
         *       "success": false,
         *       "message": "fail description"
         *     }
         *
         */
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

        
        /**
         * @api {post} /agents Create new agent
         * @apiDescription Create a new agent
         * @apiGroup Agent
         * @apiVersion 1.0.0
         *
         * @apiParam {String} firstname firstname of agent
         * @apiParam {String} surname surname of agent
         * @apiParam {String} [email] email number of agent
         * @apiParam {String} phone phone number of agent
         * @apiParam {String} [market] market assigned agent
         *
         * @apiSuccessExample {json} Success-Response:
         *     HTTP/1.1 200 OK
         *     {
         *      "success": true,
         *      "message": "Success message"
         *     }
         *
         * @apiErrorExample {json} Error-Response:
         *     HTTP/1.1 4xx Unprocessible Entity
         *     {
         *       "success": false,
         *       "message": "fail description"
         *     }
         *
         */
        agentsRouter.route('/')
            .post(function(req, res) {
                // validation
                req.checkBody('firstname', 'firstname field is required').notEmpty();
                req.checkBody('surname', 'surname field is required').notEmpty();
                req.checkBody('phone', 'phone field is required').notEmpty();
                // req.checkBody('district', 'district field is required').notEmpty();
                // req.checkBody('password', 'password field is required').notEmpty();

                var errors = req.validationErrors();
                if (errors) return res.status(422).json({success: false, errors: errors});

                //check if phone or email exist
                Agent.findOne({phone: req.body.phone})
                .then(function(agent){
                    if(agent) return res.json({success: false, message: 'registration failed. Agent already registered'});

                    var newAgent = new Agent;
                    newAgent.firstname = req.body.firstname;
                    newAgent.surname = req.body.surname;
                    newAgent.othernames = req.body.othernames;
                    newAgent.phone = req.body.phone;
                    newAgent.email = req.body.email;
                    newAgent.password = newAgent.generateHash('123456');
                    newAgent.market = req.body.market;
                    

                    newAgent.save(function(err){
                        if(err) return res.json({success: false, message: 'Error registering. try again'});
                        return res.json({success: true, message: 'registration successful', agent: newAgent})
                    });
                });
            });

        return { router: agentsRouter, event: EventEmitter };
    }

module.exports = routes;
