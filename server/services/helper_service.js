var Market = require('../models/market'),
	fs = require('fs'),
	JSONStream = require('JSONStream'),
	es = require('event-stream');


// flatten array
exports.flatten = function (arr){
    arr.reduce(function(acc, val){
        return acc.concat(Array.isArray(val) ? flatten(val) : val)
    },[])
}


exports.getdateFromNow = function(days) {
	var today = new Date();
	today.setHours(0,0,0,0);
	return new Date(today.setDate(today.getDate() - days));
}


exports.loadMarkets = function () {
	var file 	= __dirname+'/../data/market.json',
	 	stream 	= fs.createReadStream(file, {encoding: 'utf8'}),
    	parser 	= JSONStream.parse('*');
    
    return new Promise(function(resolve, reject){
        stream.pipe(parser)
        .pipe(es.mapSync(function(data) {
            Market.findOneAndUpdate(
                {name: data.MARKETS},
                {
                    name: data.MARKETS,
                    '$addToSet': { 
                        stores: {
                            store: data.STORES,
                            price: data.PRICES,
                            year: data.ReviewYear
                        }

                    }
                },
                {upsert: true, safe: true},
                function(err, result){
                    if(err) return reject();
                    resolve();
                }
            )       
	   }))
    })
}