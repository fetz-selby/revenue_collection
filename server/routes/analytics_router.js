var express = require('express'),
    event = require('events').EventEmitter,
    Payment = require('../models/payment'),
    Agent = require('../models/agent'),
    async = require('async'),
    helper  = require('../services/helper_service');

        var analyticsRouter = express.Router(),
            EventEmitter = new event();

    var routes = function(){
        analyticsRouter.route('/dashboard')
        .get(function(req, res) {
            async.parallel([
                function (cb) {
                    cb(null, []);
                    // Payment.aggregate([
                    //     { $match : { }},
                    //     { $group: {
                    //         _id: {
                    //             day: { $dayOfMonth: '$createdDate' },
                    //             month: { $month: '$createdDate' },
                    //             year: { $year: '$createdDate' }
                    //         },
                    //         y: { $sum: '$amount' }
                    //     }},
                    //     { $sort: {'_id.year': 1, '_id.month': 1,'_id.day': 1} }
                    //
                    // ], function (err, docs) {
                    //     var values = [];
                    //     if(docs){
                    //         for(var d of docs){
                    //             var mth = String(d._id.month);
                    //             var m = mth.length==1 ? '0'+mth : mth;
                    //             values.push({x: d._id.year + '-' + m + '-' + d._id.day, y: d.y});
                    //         }
                    //     }
                    //     var newdoc = [{key:'Central Market', values: values}];
                    //     cb(null, newdoc);
                    // });
                },
                function (cb) {
                    //agent count
                    Agent.count().then(function (count) {
                        cb(null, count);
                    })
                },
                function (cb) {
                    // console.log(helper.getdateFromNow(0));
                    // total today
                    Payment.aggregate([
                        { $match : { }},
                        { $group: {
                            _id: {

                            },
                            amount: { $sum: '$amount' }
                        }}

                    ], function (err, docs) {
                        var amount = docs.length > 0 ? docs[0].amount : 0;
                        cb(null, amount);
                    });
                },
                function (cb) {
                    //transact today
                    Payment.count({}).then(function (count) {
                        cb(null, count);
                    })
                },
                //transact yesterday
                function (cb) {
                    Payment.count({date: helper.getdateFromNow(1)}).then(function (count) {
                        cb(null, count);
                    })
                }
            ], function (err, result) {
                res.send({
                    chart: result[0],
                    agents: result[1],
                    totalAmount: result[2],
                    transactT: result[3],
                    transactY: result[3],
                });
            });


        });
        return { router: analyticsRouter };
    };
    module.exports = routes;
