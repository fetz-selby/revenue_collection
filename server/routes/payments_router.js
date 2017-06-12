var express = require('express'),
	People 	= require('../models/people'),
	Payment	= require('../models/Payment'),
	helper  = require('../services/helper_service');


var routes = function(){
    var paymentRouter = express.Router();

    /*
	 * 
	 *
	 *
    */
    paymentRouter.route('/')
	    .get(function(req, res){
	    	

	    });
        
    paymentRouter.route('/')
	    .get(function(req, res){
	    	

	    });

	/**
	 * @api {post} /payments Initiate payment
	 * @apiGroup Payment
	 * @apiVersion 1.0.0
	 *
	 * @apiParam {String} peopleId unique id of seller
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
	paymentRouter.route('/')
        .post(function(req, res){
        	var id = req.body.peopleId,
        		amount = 2,
        		today = helper.getdateFromNow(1);

        	People.findOne({_id: id})
        	.then(function (person) {
        		if(!person) return res.status(404).json({success: false, message: 'Person not found'});
        		Payment.findOne({people: id, 'payments.date': today},'payments.$' )
	        	.then(function (docs) {
	        		if(docs) return res.json({success: false, message:'payment for today already done'});
	        		Payment.findOneAndUpdate(
	                    { people: id },
	                    {
	                        $push:{
	                            payments: {
	                                date: today,
	                                amount: amount
	                            }
	                        }
	                    },
	                    {safe: true, upsert: true},
	                    (err, paym) => {
	                    	if(err) return res.status(500).send(err);
	                    	res.json({success: true, message: 'Payment received successfully'});
	            		}
	                )
	        	})
        	})



   //      	Earning.findOneAndUpdate(
	  //       {campaign: campaignId, user: userId, 'period.date': yesterday},
	  //       {
	  //           'period.$.date': daydate,
	  //           'period.$.network': network,
	  //           'period.$.amount': rate,
	  //           paid: false
	  //       },
	  //       {new: true},
	  //       (err, docs) => {
	  //           if(!docs){
	  //               //different date
	  //               Earning.findOneAndUpdate(
	  //                   {campaign: campaignId, user: userId},
	  //                   {
	  //                       $push:{
	  //                           period: {
	  //                               date: daydate,
	  //                               network: network,
	  //                               amount: rate
	  //                           }
	  //                       },
	  //                       paid: false
	  //                   },
	  //                   {safe: true, upsert: true},
	  //                   (err, paym) => {

	  //           		}
	  //               )
	  //           }
			// }
        	
        });

    return { router: paymentRouter };
};
module.exports = routes;