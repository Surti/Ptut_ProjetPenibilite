import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule, Http } from '@angular/http';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { RestserviceService } from './restservice.service';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    HttpModule,
    NgxChartsModule,
    FormsModule
  ],
  providers: [RestserviceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
