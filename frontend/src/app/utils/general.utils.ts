import {FormGroup} from "@angular/forms";
import {HttpHeaders} from "@angular/common/http";


export const SET_HEADERS = (attachToken: boolean = false, delayRequest: boolean = false, showAlert: boolean = false, showAlertError: boolean = false, element: string = ''): HttpHeaders => {
  let headers = new HttpHeaders();
  if (attachToken) headers = headers.set('attachToken', 'true');
  if (delayRequest) headers = headers.set('delayRequest', 'true');
  if (showAlert) headers = headers.set('showAlert', 'true');
  if (showAlertError) headers = headers.set('showAlertError', 'true');
  if (!!element) headers = headers.set('setElement', element);
  return headers;
}

export const CHECK_INPUT = (formulario: FormGroup, input: string, type: string, errorType: string = '', isSubmited: boolean = false): boolean => {
    if (!!errorType) {
        return formulario.get(input)[type] !== undefined
            && formulario.get(input)[type]
            && (formulario.get(input).dirty || formulario.get(input).touched || isSubmited)
            && formulario.get(input).errors !== null
            && formulario.get(input).errors[errorType] !== undefined
            && formulario.get(input).errors[errorType];
    }
    return formulario.get(input)[type] !== undefined && formulario.get(input)[type] && (formulario.get(input).dirty || formulario.get(input).touched || isSubmited);
}
