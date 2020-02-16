import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import { google } from '@agm/core/services/google-maps-types';
import {RouterModule, Routes} from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavbarComponent } from './navbar/navbar.component';
import { MainpageComponent } from './mainpage/mainpage.component';
import { LogInComponent } from './log-in/log-in.component';
import { RegisterComponent } from './register/register.component';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LeaderboardComponent} from './leaderboard/leaderboard.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminComponent } from './admin/admin.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProfileAdvancedComponent } from './profile-advanced/profile-advanced.component';

const appRoutes: Routes = [
  { path: '', component: LogInComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'login', component: LogInComponent},
  { path: 'mainpage', component: MainpageComponent},
  { path: 'leaderboard', component: LeaderboardComponent},
  { path: 'admin', component: AdminComponent},
  { path: 'profile', component: ProfileComponent},
  { path: 'profile-advanced', component: ProfileAdvancedComponent},
];


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavbarComponent,
    MainpageComponent,
    LogInComponent,
    RegisterComponent,
    LeaderboardComponent,
    ProfileComponent,
    AdminComponent,
    ProfileComponent,
    ProfileAdvancedComponent
  ],
  imports: [
    BrowserModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyB_6c9_2nCSLls_ru2rW-p14o0Dy0BLTxc'}),
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
