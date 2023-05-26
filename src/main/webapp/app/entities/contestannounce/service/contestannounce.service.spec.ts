import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IContestannounce } from '../contestannounce.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../contestannounce.test-samples';

import { ContestannounceService, RestContestannounce } from './contestannounce.service';

const requireRestSample: RestContestannounce = {
  ...sampleWithRequiredData,
  startdate: sampleWithRequiredData.startdate?.format(DATE_FORMAT),
  limitdate: sampleWithRequiredData.limitdate?.format(DATE_FORMAT),
};

describe('Contestannounce Service', () => {
  let service: ContestannounceService;
  let httpMock: HttpTestingController;
  let expectedResult: IContestannounce | IContestannounce[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContestannounceService);
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

    it('should create a Contestannounce', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const contestannounce = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contestannounce).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contestannounce', () => {
      const contestannounce = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contestannounce).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Contestannounce', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Contestannounce', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Contestannounce', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContestannounceToCollectionIfMissing', () => {
      it('should add a Contestannounce to an empty array', () => {
        const contestannounce: IContestannounce = sampleWithRequiredData;
        expectedResult = service.addContestannounceToCollectionIfMissing([], contestannounce);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contestannounce);
      });

      it('should not add a Contestannounce to an array that contains it', () => {
        const contestannounce: IContestannounce = sampleWithRequiredData;
        const contestannounceCollection: IContestannounce[] = [
          {
            ...contestannounce,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContestannounceToCollectionIfMissing(contestannounceCollection, contestannounce);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contestannounce to an array that doesn't contain it", () => {
        const contestannounce: IContestannounce = sampleWithRequiredData;
        const contestannounceCollection: IContestannounce[] = [sampleWithPartialData];
        expectedResult = service.addContestannounceToCollectionIfMissing(contestannounceCollection, contestannounce);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contestannounce);
      });

      it('should add only unique Contestannounce to an array', () => {
        const contestannounceArray: IContestannounce[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contestannounceCollection: IContestannounce[] = [sampleWithRequiredData];
        expectedResult = service.addContestannounceToCollectionIfMissing(contestannounceCollection, ...contestannounceArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contestannounce: IContestannounce = sampleWithRequiredData;
        const contestannounce2: IContestannounce = sampleWithPartialData;
        expectedResult = service.addContestannounceToCollectionIfMissing([], contestannounce, contestannounce2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contestannounce);
        expect(expectedResult).toContain(contestannounce2);
      });

      it('should accept null and undefined values', () => {
        const contestannounce: IContestannounce = sampleWithRequiredData;
        expectedResult = service.addContestannounceToCollectionIfMissing([], null, contestannounce, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contestannounce);
      });

      it('should return initial array if no Contestannounce is added', () => {
        const contestannounceCollection: IContestannounce[] = [sampleWithRequiredData];
        expectedResult = service.addContestannounceToCollectionIfMissing(contestannounceCollection, undefined, null);
        expect(expectedResult).toEqual(contestannounceCollection);
      });
    });

    describe('compareContestannounce', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContestannounce(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareContestannounce(entity1, entity2);
        const compareResult2 = service.compareContestannounce(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareContestannounce(entity1, entity2);
        const compareResult2 = service.compareContestannounce(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareContestannounce(entity1, entity2);
        const compareResult2 = service.compareContestannounce(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
