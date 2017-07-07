var express = require('express'),
    event = require('events').EventEmitter,
    Seller = require('../models/seller');

var routes = function(){

    var sellersRouter = express.Router(),
        EventEmitter = new event();

    /**
     * @api {get} /sellers  List sellers
     * @apiDescription Get list of all sellers
     * @apiGroup Seller
     * @apiVersion 1.0.0
     *
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "sellers": []
     *     }
     */
    sellersRouter.route('/')
        .get(function(req, res){
            //Return all people [limit this request] add ?page=1 to request
            var page = req.query.page || 1;
            var limit = 50;
            // var offset = page == 0 ? limit : limit * page;

            Seller.paginate({}, {limit: limit, page: page, sort:'-createdAt'})
            .then(function(result){
                result.sellers = result.docs;
                delete result.docs;
                res.send(result);
            })
            .catch(function(err){
                res.status(500).send({error: err});
            })
        });

    sellersRouter.route('/:id')
        .get(function(req, res){
           //Return a specific person
            Seller.findOne({_id: req.params.id})
            .then(function(person){
                res.json({person: person});
            })
            .catch(function(err){
                res.status(500).send(err)
            })
        });



    sellersRouter.route('/district/:id')
        .get(function(req, res){
            var districtId = req.params.id;
            //Return all people in a districts

        });

    sellersRouter.route('/:id')
        .put(function(req, res){
            Seller.findOneAndUpdate(
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

    sellersRouter.route('/:id')
        .delete(function(req, res){
            Seller.remove({_id: req.params.id})
            .then(function(){
                res.json({success: true})
            })
            .catch(function(err){
                res.json({success: false, error: err});
            })
        });


        /**
    	 * @api {post} /sellers Register a seller
    	 * @apiGroup Seller
    	 * @apiVersion 1.0.0
    	 *
         * @apiParam {String} firstname firstname of seller
    	 * @apiParam {String} surname surname of seller
    	 * @apiParam {String} type hawker or store
         * @apiParam {String} phone phone number
         * @apiParam {String} [mmNetwork] mobile money network
         * @apiParam {String} [mmPhone] mobile money phone number
         * @apiParam {String} uuid generated id on mobile
         * @apiParam {String} sellingSpot selling spot {table, floor}
         * @apiParam {String} agentId agent unique id
    	 * @apiParam {String} photo photo of user
    	 *
    	 * @apiSuccessExample {json} Success-Response:
    	 *     HTTP/1.1 200 OK
    	 *     {
    	 *      "success": true,
    	 *      "message": "Success message"
         *      "seller" : {
         *
         *      }
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
    sellersRouter.route('/')
        .post(function(req, res){
            // validation
            req.checkBody('firstname', 'firstname field is required').notEmpty();
            req.checkBody('surname', 'surname field is required').notEmpty();
            req.checkBody('type', 'type field is required').notEmpty();
            // req.checkBody('category', 'category field is required').notEmpty();

            var errors = req.validationErrors();
            if (errors) return res.status(422).json({success: false, errors: errors});

            req.body.agentId = '595d7fd229a93f0004bc7d7b';

            var newSeller = new Seller;

            newSeller.uuid      = req.body.uuid;
            newSeller.firstname = req.body.firstname;
            newSeller.surname = req.body.surname;
            newSeller.othernames = req.body.othernames;
            newSeller.sellingSpot = req.body.sellingSpot;
            // newSeller.section = req.body.section;
            newSeller.type = req.body.type; //hawker
            // newSeller.category = req.body.category;
            newSeller.phone = req.body.phone;
            newSeller.mobileMoney = {
                network: req.body.mmNetwork,
                number: req.body.mmPhone
            }

            newSeller.photo = req.body.photo;
            newSeller.createdBy = req.body.agentId;

            newSeller.save(function(err){
                if(err) return res.status(500).json({success: false, error: err});
                return res.json({success: true, seller: newSeller, message: 'Registration successful'});
            })
        });


        /**
         * @api {put} /sellers Update seller details
         * @apiGroup Seller
         * @apiVersion 1.0.0
         *
         * @apiParam {String} id unique id of seller
         * @apiParam {String} firstname firstname of seller
         * @apiParam {String} surname surname of seller
         * @apiParam {String} type hawker or store
         * @apiParam {String} phone phone number
         * @apiParam {String} [mmNetwork] mobile money network
         * @apiParam {String} [mmPhone] mobile money phone number
         * @apiParam {String} uuid generated id on mobile
         * @apiParam {String} sellingSpot selling spot {table, floor}
         *
         * @apiSuccessExample {json} Success-Response:
         *     HTTP/1.1 200 OK
         *     {
         *      "success": true,
         *      "message": "Success message"
         *      "seller" : {
         *
         *      }
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
    sellersRouter.route('/')
        .put(function(req, res){
            // validation
            // req.checkBody('firstname', 'firstname field is required').notEmpty();
            // req.checkBody('surname', 'surname field is required').notEmpty();
            // req.checkBody('type', 'type field is required').notEmpty();
            // req.checkBody('category', 'category field is required').notEmpty();

            var errors = req.validationErrors();
            if (errors) return res.status(422).json({success: false, errors: errors});
            req.body.agentId = '595d7fd229a93f0004bc7d7b';
            Seller.findOneAndUpdate(
                { _id: req.body.id },
                { $set: {
                    uuid : req.body.uuid,
                    firstname: req.body.firstname,
                    surname: req.body.surname,
                    othernames: req.body.othernames,
                    sellingSpot: req.body.sellingSpot,
                    // section: req.body.section,
                    type: req.body.type, //hawker
                    // category: req.body.category,
                    phone: req.body.phone,
                    mobileMoney: {
                        network: req.body.mmNetwork,
                        number: req.body.mmPhone
                    },
                    photo: req.body.photo,
                    createdBy: req.body.agentId
                }},
                {safe: true},
                function(err, result){
                    if(err) return res.status(500).json({success: false, error: err});

                    return res.json({success: true, seller: newSeller, message: 'Registration successful'});
                });
        });

    return {router: sellersRouter, event: EventEmitter};
};

module.exports = routes;
