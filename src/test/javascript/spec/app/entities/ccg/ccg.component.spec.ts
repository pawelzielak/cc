/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CcTestModule } from '../../../test.module';
import { CCGComponent } from 'app/entities/ccg/ccg.component';
import { CCGService } from 'app/entities/ccg/ccg.service';
import { CCG } from 'app/shared/model/ccg.model';

describe('Component Tests', () => {
    describe('CCG Management Component', () => {
        let comp: CCGComponent;
        let fixture: ComponentFixture<CCGComponent>;
        let service: CCGService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CcTestModule],
                declarations: [CCGComponent],
                providers: []
            })
                .overrideTemplate(CCGComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CCGComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CCGService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CCG(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cCGS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
