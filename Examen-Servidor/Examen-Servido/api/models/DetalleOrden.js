/**
 * DetalleOrden.js
 :
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    precio:{type: "number"},

    aplicaciones:{
      collection: 'Aplicaciones',
      via:'detalleOrden'
    },

    orden:{
      model: 'Ordenes'
      //unique: true
    }




  },

};

