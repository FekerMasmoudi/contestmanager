import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEducationlevel } from '../educationlevel.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../educationlevel.test-samples';

import { EducationlevelService } from './educationlevel.service';

const requireRestSample: IEducationlevel = {
  ...sampleWithRequiredData,
};

describe('Educationlevel Service', () => {
  let service: EducationlevelService;
  let httpMock: HttpTestingController;
  let expectedResult: IEducationlevel | IEducationlevel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EducationlevelService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Educationlevel', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const educationlevel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(educationlevel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Educationlevel', () => {
      const educationlevel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(educationlevel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Educationlevel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Educationlevel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Educationlevel', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEducationlevelToCollectionIfMissing', () => {
      it('should add a Educationlevel to an empty array', () => {
        const educationlevel: IEducationlevel = sampleWithRequiredData;
        expectedResult = service.addEducationlevelToCollectionIfMissing([], educationlevel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(educationlevel);
      });

      it('should not add a Educationlevel to an array that contains it', () => {
        const educationlevel: IEducationlevel = sampleWithRequiredData;
        const educationlevelCollection: IEducationlevel[] = [
          {
            ...educationlevel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEducationlevelToCollectionIfMissing(educationlevelCollection, educationlevel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Educationlevel to an array that doesn't contain it", () => {
        const educationlevel: IEducationlevel = sampleWithRequiredData;
        const educationlevelCollection: IEducationlevel[] = [sampleWithPartialData];
        expectedResult = service.addEducationlevelToCollectionIfMissing(educationlevelCollection, educationlevel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(educationlevel);
      });

      it('should add only unique Educationlevel to an array', () => {
        const educationlevelArray: IEducationlevel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const educationlevelCollection: IEducationlevel[] = [sampleWithRequiredData];
        expectedResult = service.addEducationlevelToCollectionIfMissing(educationlevelCollection, ...educationlevelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const educationlevel: IEducationlevel = sampleWithRequiredData;
        const educationlevel2: IEducationlevel = sampleWithPartialData;
        expectedResult = service.addEducationlevelToCollectionIfMissing([], educationlevel, educationlevel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(educationlevel);
        expect(expectedResult).toContain(educationlevel2);
      });

      it('should accept null and undefined values', () => {
        const educationlevel: IEducationlevel = sampleWithRequiredData;
        expectedResult = service.addEducationlevelToCollectionIfMissing([], null, educationlevel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(educationlevel);
      });

      it('should return initial array if no Educationlevel is added', () => {
        const educationlevelCollection: IEducationlevel[] = [sampleWithRequiredData];
        expectedResult = service.addEducationlevelToCollectionIfMissing(educationlevelCollection, undefined, null);
        expect(expectedResult).toEqual(educationlevelCollection);
      });
    });

    describe('compareEducationlevel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEducationlevel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareEducationlevel(entity1, entity2);
        const compareResult2 = service.compareEducationlevel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareEducationlevel(entity1, entity2);
        const compareResult2 = service.compareEducationlevel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareEducationlevel(entity1, entity2);
        const compareResult2 = service.compareEducationlevel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
