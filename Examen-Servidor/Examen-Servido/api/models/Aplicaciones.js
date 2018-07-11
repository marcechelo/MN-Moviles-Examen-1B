/**
 * Aplicaciones.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    nombreApp: {type:"string"},
    pesoGigasApp: {type:"number"},
    version: {type:"number"},
    urlApp: {type:"string"},
    fechaLanzamientoApp: {type:"string"},
    costo: {type:"number"},
    //latitud: {type:"number"},
    //longitud: {type:"number"},
    foto: {type:"string"},

    sistemaOperativoId: {
      model:"SistemaOperativo"
    },

    detalleOrden:{
      collection: 'DetalleOrden',
      via:'aplicaciones'
    },

    fotos:{
      collection:'Fotos',
      via:'aplicaciones'
    }

  },

};

