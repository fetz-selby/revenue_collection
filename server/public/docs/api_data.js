define({ "api": [  {    "type": "post",    "url": "/payments",    "title": "Initiate payment",    "group": "Payment",    "version": "1.0.0",    "parameter": {      "fields": {        "Parameter": [          {            "group": "Parameter",            "type": "String",            "optional": false,            "field": "peopleId",            "description": "<p>unique id of seller</p>"          }        ]      }    },    "success": {      "examples": [        {          "title": "Success-Response:",          "content": "HTTP/1.1 200 OK\n{\n \"success\": true,\n \"message\": \"Success message\"\n}",          "type": "json"        }      ]    },    "error": {      "examples": [        {          "title": "Error-Response:",          "content": "HTTP/1.1 4xx Unprocessible Entity\n{\n  \"success\": false,\n  \"message\": \"fail description\"\n}",          "type": "json"        }      ]    },    "filename": "routes/payments_router.js",    "groupTitle": "Payment",    "name": "PostPayments"  }] });
