var express = require('express'),
	Seller 	= require('../models/seller'),
	Payment	= require('../models/payment'),
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
	 * @apiParam {String} sellerId unique id of seller
	 * @apiParam {String} type daily or yearly
	 * @apiParam {String} amount amount being paid
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
        	var id = req.body.sellerId,
        		amount = req.body.amount,
        		today = helper.getdateFromNow(1)
				type = req.body.type;

				if(type == 'daily'){
					var r = {
						sellingOnTable: 1,
						sellingOntheFloor: 0.5,
						shops: 2
					}
				}





        	// Seller.findOne({_id: id})
        	// .then(function (person) {
        		// if(!person) return res.status(404).json({success: false, message: 'Person not found'});
	        		var newPayment = new Payment;
	                newPayment.seller =  id;
                    newPayment.date = today;
                    newPayment.amount = amount;
					newPayment.type = type;

	                newPayment.save((err, paym) => {
	                    	if(err) return res.status(500).send(err);
	                    	res.json({success: true, message: 'Payment received successfully'});
	            		}
	                )

        	// })



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
