/**
 * SistemaOperativo.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    nombreSo:{type: "string"},
    versionApi:{type: "number"},
    fechaLanzamiento:{type: "string"},
    pesoGigasSo:{type: "number"},
    instalado:{type: "number"},

    aplicaciones:{
      collection:"Aplicaciones",
      via:"sistemaOperativoId"
    },

    usuario:{
      model: 'Usuario',

    }
  },

};

