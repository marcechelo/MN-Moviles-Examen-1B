/**
 * Ordenes.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    fecha:{type: "string"},
    total:{type: "number"},
    estado:{type: "number"},
    // ubicacionEntrega:{type: "string"},
    // Ubicacion de Entrega
    latitud: {type:"number"},
    longitud: {type:"number"},
    costoDelivery:{type: "number"},
    fechaEntrega:{type: "string"},

    usuario:{
      model:'Usuario'

    },

    detalleOrden:{
      collection:'DetalleOrden',
      via:'orden'
    }


  },

};

