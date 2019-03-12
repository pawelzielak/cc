/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CcTestModule } from '../../../test.module';
import { CCGDetailComponent } from 'app/entities/ccg/ccg-detail.component';
import { CCG } from 'app/shared/model/ccg.model';

describe('Component Tests', () => {
    describe('CCG Management Detail Component', () => {
        let comp: CCGDetailComponent;
        let fixture: ComponentFixture<CCGDetailComponent>;
        const route = ({ data: of({ cCG: new CCG(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CcTestModule],
                declarations: [CCGDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CCGDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CCGDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cCG).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
