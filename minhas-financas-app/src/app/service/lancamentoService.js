import ApiService from '../apiservice'

export default class LancamentoService extends ApiService {

    constructor() {
        super('/api/lancamentos')
    }

}