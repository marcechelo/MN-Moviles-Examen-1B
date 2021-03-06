/**
 * Aplicaciones.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    idApp: {
      type:"number",
      autoIncrement: true
    },
    nombreApp: {type:"string"},
    pesoGigasApp: {type:"number"},
    version: {type:"number"},
    urlApp: {type:"string"},
    fechaLanzamientoApp: {type:"string"},
    costo: {type:"number"},
    sistemaOperativoId: {
      model:"SistemaOperativo"
    }
  },

};

