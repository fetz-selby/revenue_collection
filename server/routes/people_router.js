var express = require('express'),
    event = require('events').EventEmitter,
    People = require('../models/people');

var routes = function(){

    var peoplesRouter = express.Router(),
        EventEmitter = new event();
    

    peoplesRouter.route('/')
        .get(function(req, res){  
            //Return all people [limit this request] add ?page=1 to request
            var page = req.query.page || 1;
            var limit = 20;
//            var offset = page == 0 ? limit : limit * page;
            
        
            People.paginate({}, {limit: limit, page: page, sort:'-createdAt'})
            .then(function(result){
                result.people = result.docs;
                delete result.docs;
                res.send(result);
            })
            .catch(function(err){
                res.status(500).send({error: err});
            })
        });   

    peoplesRouter.route('/:id')
        .get(function(req, res){
           //Return a specific person
            People.findOne({_id: req.params.id})
            .then(function(person){
                res.json({person: person});
            })
            .catch(function(err){
                res.status(500).send(err)
            })
        }); 

 

    peoplesRouter.route('/district/:id')
        .get(function(req, res){
            var districtId = req.params.id;
            //Return all people in a districts
            
        }); 

    peoplesRouter.route('/:id')
        .put(function(req, res){
            People.findOneAndUpdate(
                { _id: req.params.id },
                { $set: {
                    
                }},
                { safe: true },
                function(err, doc){
                    if(err) return res.status(500).json({success: false, error: err});
                    return res.json({success: true, message: 'Update successful'});
                }
            )    
        
        });

    peoplesRouter.route('/:id')
        .delete(function(req, res){
            People.remove({_id: req.params.id})
            .then(function(){
                res.json({success: true})
            })
            .catch(function(err){
                res.json({success: false, error: err});
            })
        });

    peoplesRouter.route('/')
        .post(function(req, res){
            // validation
            req.checkBody('firstname', 'firstname field is required').notEmpty();
            req.checkBody('surname', 'surname field is required').notEmpty();
            req.checkBody('type', 'type field is required').notEmpty();
            req.checkBody('category', 'category field is required').notEmpty();

            var errors = req.validationErrors();
            if (errors) return res.status(422).json({success: false, errors: errors});
        
            var newPeople = new People;
            
            newPeople.firstname = req.body.firstname;
            newPeople.surname = req.body.surname;
            newPeople.othernames = req.body.othernames;
            newPeople.section = req.body.section;
            newPeople.type = req.body.type; //hawker
            newPeople.category = req.body.category;
            newPeople.phone = req.body.phone;
            newPeople.mobileMoney = {
                network: req.body.mmNetwork,
                number: req.body.mmNumber
            }
           
            newPeople.photo = req.body.photo;
            newPeople.createdBy = req.body.agentId;
        
            newPeople.save(function(err){
                if(err) return res.status(500).json({success: false, error: err});
                return res.json({success: true, message: 'Registration successful'});
            })
        });
    
    return {router: peoplesRouter, event: EventEmitter};
};

module.exports = routes;