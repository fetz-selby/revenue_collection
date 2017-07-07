var express = require('express'),
	Market	= require('../models/market'),
	Agent	= require('../models/agent'),
	helper  = require('../services/helper_service');


var routes = function(){
    var marketRouter = express.Router();

	/**
     * @api {get} /markets  List markets
     * @apiDescription Get list of all markets
     * @apiGroup Market
     * @apiVersion 1.0.0
     *
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "markets": []
     *     }
     */
	marketRouter.route('/')
    .get(function(req, res){
    	Market.find({}, 'name')
    	.then(function(markets){
    		return res.json({markets: markets});
    	})

    });


    /**
     * @api {get} /markets/:id/agents  List agents for a market
     * @apiDescription Get list of all agents assigned a market
     * @apiGroup Market
     * @apiVersion 1.0.0
     *
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "agents": []
     *     }
     */
    marketRouter.route('/:id/agents')
    .get(function(req, res){
    	Agent.find({market: req.params.id})
    	.then(function(agents){
    		return res.json({agents: agents});
    	})

    });


    /**
     * @api {get} /markets/:id  Market details
     * @apiDescription Single market details
     * @apiGroup Market
     * @apiVersion 1.0.0
     *
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "market": {}
     *     }
     */
    marketRouter.route('/:id')
    .get(function(req, res){
    	Market.findOne({_id: req.params.id})
    	.then(function(market){
    		return res.json({market: market});
    	});
    });

   

    return { router: marketRouter };
};
module.exports = routes;
