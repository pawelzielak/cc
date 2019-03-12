/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CcTestModule } from '../../../test.module';
import { RadioDetailComponent } from 'app/entities/radio/radio-detail.component';
import { Radio } from 'app/shared/model/radio.model';

describe('Component Tests', () => {
    describe('Radio Management Detail Component', () => {
        let comp: RadioDetailComponent;
        let fixture: ComponentFixture<RadioDetailComponent>;
        const route = ({ data: of({ radio: new Radio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CcTestModule],
                declarations: [RadioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RadioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RadioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.radio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
