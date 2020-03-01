import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  value: String;

  constructor(private keycloak: KeycloakService) {
    const helper = new JwtHelperService();



    keycloak.getToken().then(token => {
      const decodedToken = helper.decodeToken(token);
      this.value = decodedToken;

      //this.value = atob(token.split('.')[1]);
    });

  }
}
